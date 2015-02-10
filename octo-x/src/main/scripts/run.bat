ECHO OFF
TITLE ${artifactId} console
CLS
start javaw -jar -Djava.library.path="${release.natives}" "${finalName}"