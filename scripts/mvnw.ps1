Param(
  [Parameter(ValueFromRemainingArguments=$true)]
  [String[]]$Args
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

function Get-RepoRoot(){
  return (Join-Path $PSScriptRoot '..')
}

function Ensure-Maven(){
  $repo = Get-RepoRoot
  $mavenDir = Join-Path $repo '.maven/apache-maven-3.9.4'
  $mvnCmd = Join-Path $mavenDir 'bin\mvn.cmd'
  if (Test-Path $mvnCmd){ return $mvnCmd }

  Write-Host "[mvnw] Maven not found locally. Downloading Apache Maven 3.9.4..." -ForegroundColor Cyan
  $zipUrl = 'https://archive.apache.org/dist/maven/maven-3/3.9.4/binaries/apache-maven-3.9.4-bin.zip'
  $zipPath = Join-Path $repo 'apache-maven-3.9.4-bin.zip'
  Invoke-WebRequest -Uri $zipUrl -OutFile $zipPath -UseBasicParsing
  Write-Host "[mvnw] Extracting..." -ForegroundColor Cyan
  Expand-Archive -Path $zipPath -DestinationPath (Join-Path $repo '.maven') -Force
  Remove-Item $zipPath -Force
  if (-not (Test-Path $mvnCmd)){
    throw "Maven download failed or structure unexpected. Look in .maven folder."
  }
  return $mvnCmd
}

try{
  $mvn = Ensure-Maven
  # Build argument list and start Maven in current working directory
  $argline = $Args -join ' '
  if ([string]::IsNullOrWhiteSpace($argline)) { $argline = '' }
  # Use Start-Process so it behaves like mvn: create a new window for interactive output
  $proc = Start-Process -FilePath $mvn -ArgumentList $Args -NoNewWindow -PassThru -Wait
  exit $proc.ExitCode
} catch {
  Write-Error "mvnw error: $_"
  exit 1
}
