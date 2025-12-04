# =====================================================================
# FortePix — Diagnostic Nuclear Scan (Reduced but Lethal Edition)
# Creates Diagnosis folder with structured output from:
# - Maven Build + Dependency Analyzer
# - SpotBugs (with HTML report)
# - PMD + CPD
# - OWASP Dependency Check
# - OpenRewrite (dry run)
# =====================================================================

Write-Host "`n=========================="
Write-Host " FortePix Diagnostic Scan"
Write-Host "==========================`n"

# Base folder
$base = Get-Location
$diag = "$base\Diagnosis"

# Create folders
$folders = @(
    "$diag\01-maven",
    "$diag\02-spotbugs",
    "$diag\03-pmd",
    "$diag\04-owasp",
    "$diag\05-rewrite"
)

foreach ($f in $folders) {
    if (!(Test-Path $f)) { New-Item -ItemType Directory -Path $f | Out-Null }
}

# ------------------------
# 1. MAVEN BUILD + DEP ANALYSIS
# ------------------------
Write-Host "`n[1/5] Running Maven clean package..."
mvn -DskipTests clean package `
    > "$diag\01-maven\build.log" 2>&1

Write-Host "[1/5] Running Maven dependency:analyze..."
mvn dependency:analyze `
    > "$diag\01-maven\dependency-analysis.txt" 2>&1


# ------------------------
# 2. SPOTBUGS
# ------------------------
Write-Host "`n[2/5] Running SpotBugs scan..."
mvn com.github.spotbugs:spotbugs-maven-plugin:4.7.3.4:spotbugs `
    > "$diag\02-spotbugs\spotbugs.log" 2>&1

# Copy HTML if exists
$spotHtml = "$base\target\spotbugsHtml.html"
if (Test-Path $spotHtml) {
    Copy-Item $spotHtml "$diag\02-spotbugs\spotbugs.html"
}


# ------------------------
# 3. PMD + CPD
# ------------------------
Write-Host "`n[3/5] Running PMD..."
mvn pmd:pmd `
    > "$diag\03-pmd\pmd.log" 2>&1

Write-Host "[3/5] Running CPD (duplicate code)..."
mvn pmd:cpd `
    > "$diag\03-pmd\cpd.log" 2>&1

# Move reports if they exist
$pmdHtml = "$base\target\site\pmd.html"
$cpdHtml = "$base\target\site\cpd.html"

if (Test-Path $pmdHtml) {
    Copy-Item $pmdHtml "$diag\03-pmd\pmd.html"
}
if (Test-Path $cpdHtml) {
    Copy-Item $cpdHtml "$diag\03-pmd\cpd.html"
}


# ------------------------
# 4. OWASP DEPENDENCY CHECK
# ------------------------
Write-Host "`n[4/5] Running OWASP Dependency Check..."
mvn org.owasp:dependency-check-maven:check `
    > "$diag\04-owasp\owasp.log" 2>&1

# Move reports
$owaspHtml = "$base\target\dependency-check-report.html"
$owaspXml = "$base\target\dependency-check-report.xml"

if (Test-Path $owaspHtml) {
    Copy-Item $owaspHtml "$diag\04-owasp\dependency-check-report.html"
}
if (Test-Path $owaspXml) {
    Copy-Item $owaspXml "$diag\04-owasp\dependency-check-report.xml"
}


# ------------------------
# 5. OPENREWRITE (dry run)
# ------------------------
Write-Host "`n[5/5] Running OpenRewrite dry run..."
mvn org.openrewrite.maven:rewrite-maven-plugin:dryRun `
    > "$diag\05-rewrite\rewrite-dryrun.log" 2>&1


# ------------------------
# DONE
# ------------------------
Write-Host "`n========================================"
Write-Host "   FortePix Diagnostic COMPLETE"
Write-Host " Reports saved to: Diagnosis/"
Write-Host "========================================`n"
