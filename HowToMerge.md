1.  Commit your changes in your branch (right now I assume you have your branch set as the working copy)

2.  Right click on the project folder in the Package Explorer view and select Team/Switch to another branch/tag

3.  Select the trunk folder (don't select the src folder; just grab the whole trunk folder)

4.  Expand the directory structure and right-click on the src folder.  Select Team/Merge...

5.  In the "From:" url field, select the src folder in the branch that you committed your changes to.

6.  Click the Show log button and select the version that includes your changes (should be the latest one)

7.  Click Merge.

8.  Merge merges the files into your working set so you can test them before you check them into trunk.

9.  Once all the tests pass, Commit the changes

10.Switch your working copy back to your branch (right-click -> Team/Switch to another branch/tag)


This should do the trick.  I think after you've done this a couple of times, it should become clearer.