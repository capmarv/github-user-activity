# GitHub User Activity CLI

A Java command-line application that fetches and displays the recent activity of a GitHub user using the GitHub API.

# How it works

* Takes a GitHub username as input
* Calls the GitHub API
* Parses the JSON response
* Displays activity in readable format

# Usage

Compile:

```id="c2r8o1"
javac Main.java
```

Run:

```id="x9k2m7"
java Main <username>
```

Example:

```id="k8p3f2"
java Main capmarv
```

# Output

```id="b7n1q9"
GitHub Activity for torvalds:

Github-User-Activity of capmarv
- Pushed to capmarv/github-user-activity
- Pushed to capmarv/github-user-activity
- Created repository capmarv/github-user-activity
- Pushed to capmarv/CLI
```

# Features

* Shows recent GitHub activity
* Supports multiple event types (Push, Create, Watch, Delete)
* Handles unknown events
* Handles invalid usernames

# Limitations

* Shows only recent activity (currently upto 10 events per page for example usage but can be changed to ~30) 
* Maximum ~300 events available
* Uses manual JSON parsing

# Tech

* Java
* GitHub 


# Author

capmarv
