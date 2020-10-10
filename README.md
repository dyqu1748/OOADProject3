# OOADProject3

### Members:
Anna Nuggehalli, Dylan Quach, Casey Tran

## Assumptions Made:
- User is running the program on IntelliJ IDEA or in a terminal.
- JDK is version 8 or newer.
- Customer will make their original and revised ordered (save Business customers) back to back (in the same function call) if their roll(s) is out of stock.
- The roll store is capable of displaying (printing out) order information and daily/monthly statistics.

## Issues During Dev:
- Figuring out how to track the stock, cash made, and rolls ordered. Initially thought of using multiple ArrayLists, but changed to HashMaps due to their similarity to Python's Dictionaries.
- Deciding where to output all the statistics. Initially thought of outputting everything within main, but decided otherwise due to the amount of code it would've taken up in main. Gave the task to the roll store as it held all of the necessary information already.
- Trouble on figuring out how output should be laid out. Since we did not want the stock announcements to be out of place, we decided to have the store print out when it was open and closed and have all of the individual roll order info be displayed in between those two statements. When rolls ran out, it would be displayed after individual roll orders.

## Instructions to Run Application:
#### Instructions for Running Application in a Terminal: ####
- Open the terminal for your respective device (Command Prompt on Windows, Terminal on mac or Linux).
- Change the current directory to the location of OOADProject3.jar (i.e. cd OOADProject3/OOADProject3/out/artifacts/OOADProject3_jar).
- Type in and enter java -jar OOADProject3.jar into the terminal to run the program.
- The program will execute and display a message asking for the maximum stock of the rolls. Please enter either 30, 45, or 60 to meet the requirements of the project. After it has completed running, a "output.txt" file will be created in the artifacts folder above the executable.
#### Instructions for Running Application in IntelliJ IDEA: ####
- Open the project in IntelliJ by right-clicking the OOADProject3 folder and selecting "Open Folder as IntelliJ IDEA Community Edition Project".
- Locate OOADProject3/out/artifacts/OOADProject3_jar/OOADProject3.jar in the "Project" tab.
- Right-click on the OOADProject3.jar file and select "Run 'OOADProject3.jar'".
- The program will execute and display a message asking for the maximum stock of the rolls. Please enter either 30, 45, or 60 to meet the requirements of the project. After completion, a "output.txt" file will be created in the OOADProject3 folder.
