@echo off
setx JAVA_HOME "C:\Program Files\Java\jdk-24"
setx PATH "%PATH%;%%JAVA_HOME%%\bin"
echo JDK 24 environment variables have been set up.
echo Please restart your terminal/IDE for changes to take effect.
pause