SET JAVA_HOME=c:\Program Files\Java\jdk1.7.0\
SET ANT_LOCATION=d:\Programok\EclipseJuno\plugins\org.apache.ant_1.8.3.v20120321-1730\bin\

@cd hu.documaison.gui
@REM copy ..\hu.documaison.dal\src\log4j.xml bin\hu\documaison\gui\log4j.xml
%ANT_LOCATION%\ant DocuMaisonWindow
@cd ..