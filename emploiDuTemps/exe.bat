javac -d "build" -cp "package/scheduleio.jar" schedule/*.java
cd build
jar cfe scheduleExe.jar schedule.Executable .
java -cp "../package/scheduleio.jar;scheduleExe.jar" schedule.Executable "../activities.txt" "../constraints.txt" "../meet.txt" "../maxspan.txt"
pause