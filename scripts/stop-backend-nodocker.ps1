Set-StrictMode -Version Latest
$ErrorActionPreference = "SilentlyContinue"

$ports = @(8761, 8091, 8085, 8087, 8081, 8092, 8093, 8094)

foreach($port in $ports){
  Write-Host "[stop] Port $port" -ForegroundColor Cyan
  $pids = Get-NetTCPConnection -LocalPort $port -State Listen | Select-Object -ExpandProperty OwningProcess -Unique
  foreach($pid in $pids){
    try { Stop-Process -Id $pid -Force } catch {}
  }
}

Write-Host "[ok] Stop attempts done." -ForegroundColor Green
