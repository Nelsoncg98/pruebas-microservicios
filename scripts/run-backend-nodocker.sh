#!/usr/bin/env bash
set -euo pipefail

# Script para arrancar todos los microservicios desde Bash (Linux/macOS/Git Bash)
# Crea ./logs/*.log y guarda PIDs en ./pids.txt

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT_DIR"

LOG_DIR="$ROOT_DIR/logs"
PIDS_FILE="$ROOT_DIR/pids.txt"

mkdir -p "$LOG_DIR"
rm -f "$PIDS_FILE"

# Detectar mvn (prioriza mvn en PATH)
if command -v mvn >/dev/null 2>&1; then
  MVN_CMD="mvn"
else
  echo "ERROR: 'mvn' no está en PATH. Instala Maven o ejecuta desde PowerShell con el .ps1" >&2
  exit 1
fi

start_module() {
  name=$1
  dir=$2
  log="$LOG_DIR/${dir//\//-}.log"
  echo "[run] $name -> $dir (log: $log)"
  ( cd "$dir" && $MVN_CMD spring-boot:run > "$log" 2>&1 & echo $! >> "$PIDS_FILE" )
}

cleanup() {
  echo "\n[stop] Deteniendo servicios..."
  if [ -f "$PIDS_FILE" ]; then
    xargs -a "$PIDS_FILE" -r kill || true
    rm -f "$PIDS_FILE"
  fi
  echo "[stop] Hecho."
}

trap cleanup EXIT INT TERM

# Orden de inicio (ajusta si cambias módulos)
start_module "Eureka" "EurekaServerN"
sleep 7
start_module "ms-medico" "ms-medico"
sleep 2
start_module "ms-horariomedico" "ms-horariomedico"
sleep 2
start_module "ms-programacionmedica" "ms-programacionmedica"
sleep 2
start_module "ms-personaladministrativo" "ms-personaladministrativo"
sleep 2
start_module "ms-paciente" "ms-paciente"
sleep 2
start_module "ms-enfermera" "ms-enfermera"
sleep 2
start_module "ms-carritohorariomedico" "ms-carritohorariomedico"

echo "[ok] Servicios arrancados. Logs en $LOG_DIR, PIDs en $PIDS_FILE"
echo "Presiona Ctrl+C para detener y limpiar."

# Mostrar logs en vivo (abre otro terminal si no quieres hacer tail)
tail -n +1 -f "$LOG_DIR"/*.log &
wait
