This is the "assignments" repo for C343 that will contain the startercode for Assignments and Labs

## To Get Started
### Create your remote private repo on github.iu.edu within this organization
1. Create a private repo within the CSCI-C343-Fall2025 organization. Make sure this private repo of yours is named in the following format: \<username\>. For example, if your IU username is jghafoor, your private github repo within this organization should be named jghafoor.

### Clone the course assignments and your private repos to your laptop
   
1. Open the terminal in your computer and type the following commands to clone this "assignments" repository
```console
foo@bar:~$ cd IdeaProjects
foo@bar:IdeaProjects$ mkdir C343
foo@bar:IdeaProjects$ cd C343
foo@bar:C343$ git init
Initialized empty Git repository in /Users/foo/IdeaProjects/C343/.git/
foo@bar:C343$ git clone https://github.iu.edu/CSCI-C343-Fall2025/assignments.git
```
2. Then clone your own private repo via running the following command. Make sure to replace username with your own IU username.
```console
foo@bar:C343$ git clone https://github.iu.edu/CSCI-C343-Fall2025/username.git
```
3. Check for existing remotes. This step is to make sure the remotes are intact for pulling and pushing.
```console
foo@bar:C343$ cd assignments
foo@bar:assignments$ git remote -v
origin	https://github.iu.edu/CSCI-C343-Fall2025/assignments.git (fetch)
origin	https://github.iu.edu/CSCI-C343-Fall2025/assignments.git (push)
foo@bar:assignments$ cd ..
foo@bar:C343$ cd username  # Make sure to replace username with your own IU username
foo@bar:username$ git remote -v
origin	https://github.iu.edu/CSCI-C343-Fall2025/username.git (fetch)
origin	https://github.iu.edu/CSCI-C343-Fall2025/username.git (push)
```

## To Pull from the "assignments" repo
To Pull or to make sure that the latest changes from the "assignments" repo have been pulled, run commands:
```console
foo@bar:~$ cd IdeaProjects
foo@bar:IdeaProjects$ cd C343
foo@bar:C343$ cd assignments
foo@bar:assignments$ git pull
```
You won't be able to push anything to this "assignments" repo, as this is for the instructor and TAs to manage. Furthermore, you are strictly advised to not make any local changes/edits within the "assignments" folder in your local machine.

## To Push to your "username" repo
With every new pull of an assignment from the "assignments" repo, you are expected to copy that particular assignment folder (e.g. A1 or L1) over from within the "assignments" folder to your "username" folder, on your local machine. You may then open your assignment folder (e.g. A1 or L1) from within the "username" folder on your local machine, as a project on IntelliJ, and work on the assignment.

Once you are ready to submit, you may push the changes you've made to your "username" repo by running the following commands:
```console
foo@bar:~$ cd IdeaProjects
foo@bar:IdeaProjects$ cd C343
foo@bar:C343$ cd username
foo@bar:username$ git add <LIST OF FILES/FOLDERS YOU WANT TO COMMIT SEPARATED BY SPACE>
foo@bar:username$ git commit -m "<MESSAGE IN DOUBLE QUOTES>"
foo@bar:username$ git push
```
You should strictly abstain from manually editing your "username" repo on github.iu.edu; interaction with this remote repo of yours should be through push/pull/fetch only.

## Must things to ensure:
1. You are not allowed to push anything to the "assignments" repo.
2. You must not make local changes within the "assignments" folder in your local machine. Keep this folder as just a place to get an updated copy of the assignments repo via git pulling from its remote.
3. You must make sure to copy over the particular assignment folders (e.g. A1 or L1) over from within the "assignments" folder on your local machine to your "username" folder on your local machine.
4. Ensure that you open your assignment folder (e.g. A1 or L1) from within the "username" folder on your local machine, as a project on IntelliJ, to work on the assignment.
5. You must not manually edit your "username" repo on github.iu.edu; interaction with this remote repo of yours should be through push/pull/fetch only.

## FAQ: 
### Q1) How to fix the following error:
```console
To https://github.iu.edu/CSCI-C343-Fall2025/username.git
 ! [rejected]        main -> main (fetch first)
error: failed to push some refs to 'https://github.iu.edu/CSCI-C343-Fall2025/username.git'
hint: Updates were rejected because the remote contains work that you do
hint: not have locally. This is usually caused by another repository pushing
hint: to the same ref. You may want to first integrate the remote changes
hint: (e.g., 'git pull ...') before pushing again.
hint: See the 'Note about fast-forwards' in 'git push --help' for details.
```
### A1)
This error occurs when your "username" repo is some commits ahead of what's on your local machine. To fix this error:
1. NECESSARY PRECAUTION: Make a copy of your "username" folder as a "username-copy" folder to compare with what eventually pushes. This is a necessary precautionary step in-case you somehow lose some/all of your work.
2. Run the following command:
```console
foo@bar:username$ git pull --rebase origin main
```
3. If rebase is successful i.e. you get
```console
Successfully rebased and updated refs/heads/main.
```
Then just push, and you're done.
```console
foo@bar:username$ git push
```
4. Else if there are merge conflicts, we need to resolve them first. Assuming your local machine has all changes up-to-date, run:
```console
foo@bar:username$ git checkout --ours .
foo@bar:username$ git add . # stages changes within the current directory and its subdirectories
foo@bar:username$ git rebase --continue
```
Repeat the above three lines until rebase is successful and then you'll push, and you're done.
```console
Successfully rebased and updated refs/heads/main.
foo@bar:username$ git push
```
Note: If you want to throw all the changes and just keep github (remote) version, in the above sequence of commands, do the following inplace of first command:
```console
foo@bar:username$ git checkout --theirs .
```
Otherwise, if you want to manually decide for each merge conflict whether to keep the local version or the remote version, you would check which files are conflicted using "git status" and then decide if to checkout with local changes (using option --ours) or keep remote version (using option --theirs):
```console
foo@bar:username$ git status  # shows conflicted files
foo@bar:username$ git checkout --ours/theirs <FILENAME>
foo@bar:username$ git rebase --continue
```
This would be repeated until rebase is successful and then you'll push, and you're done.
```console
Successfully rebased and updated refs/heads/main.
foo@bar:username$ git push
```
5. At the end, do once compare content in "username-copy" folder with what's in "username". If there are necessary edits that were overwritten, manually rewrite these changes by opening the project on IntelliJ of the particular assignment folder (e.g. A1 or L1) from within the "username" folder on your local machine. And then commit and push the changes.
```console
foo@bar:username$ git add <LIST OF FILES/FOLDERS YOU WANT TO COMMIT SEPARATED BY SPACE>
foo@bar:username$ git commit -m "<MESSAGE IN DOUBLE QUOTES>"
foo@bar:username$ git push
```
If you still face any error in this process, please don't hesitate to ask on Discord or stop by in office hours.
