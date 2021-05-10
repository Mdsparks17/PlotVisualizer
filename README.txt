PlotVisualizer App:

This Java application does 3 main things
- Retrieve a list of files from a directory and all subdirectories
- Filters that list by string criterion in the file/path name
- Allows users to open the images within Java

The primary audience for this app are researches at the EPA who required a faster way of finding plots, and sorting them, without the hassle of needing to manually click through a directory tree. This app is useful for navigating through dense directory trees.

Feel free to distribute, edit, adapt, as long as credit is given. 


Quickstart:
- Grab the PlotVisualizer.jar from /dist/PlotVisualizer.jar
- Open/Execute the jar (through the command line try java -jar PlotVisualizer.jar)
- Select Root Directory; The more files in the directory the longer the app will need to load
- Filter which sub-strings to look for by comma seperated case sensitive 'tags'. NO SPACES (IE: looking for .png files where the path contains 'cat' enter in ".png,cat" and hit the search button)
- Click on the jList to preview the file, click 'Open Desktop' to open the file with the default desktop application, click 'Open Java' to open the file in a new java window, click 'Open Animation' to open all currently selected files in a new java window where they can then be iterated.


Sources Used:
https://github.com/reshadat/SimpleImageInfo
