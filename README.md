***Project***
	testInProgress_Extended

***Authors***
	Adam Walls 
	Ricardo Munoz-Torrez

***Description***
	Jenkins has two main components: client and server.
	Client sends requests to the server and the server sends the results back to the client.
	TestInProgress is structured very similarly.
	
	The original testInProgress (TIP) plugin allows a user to view the status of their junit tests as they are run.
	It can be found via the following link:
		https://wiki.jenkins.io/display/JENKINS/Test+In+Progress+Plugin
	That source code consists of three modules/packages: server, client, and plugin.
	Server does the actual work of building and running the junit tests.
	Client listens for the server sending the resulting build and test messages run in parallel and will relay its 
	contents to the plugin package.
	Plugin is the user interface displaying the messages the client receives and displays them on
	the testInProgress page on the jenkins page.

	The extended TIP builds on this and allows a user to decide if they want to stop testing  or continue.
	If the user selects to stop, it will simply exit execution of the junit tests.
	If the user selects to continue, it will continue testing.
	
***Development Tools/Environments Used***
	**Adam**
		Microsoft Surface Pro 2 w/ Windows 8.1 OS
 		University of Kentucky virtual machine on openstack which used Ubuntu OS
		java 8
		maven 3.3.9
		Jenkins 
		IntelliJ
		Eclipse
		
	**Ricardo**
		 
***How to install the plugin***
	**What you will need**
		Jenkins installed on your machine
		Maven or something similar like testNG installed on your machine
		testInProgress_Extended source code:
			https://github.com/Awallky/testInProgress_Extended.git
	Assuming that you have a working, testable project, install the test in progress plugin and 
	modify your test project as described in the test in progress wiki page:
		https://wiki.jenkins.io/display/JENKINS/Test+In+Progress+Plugin
	*Note: you will find .class files in the ProgressAllTestsSuite. You need to replace the generic 
	      .class file names they have with your own .class files associated with your test suite.*
	Once you have implemented those changes, clone the testInProgress_Extended plugin from the link above.
	If you wish to skip these steps, you may instead use the mystacktest project, which is already set to work with the TIP plugin, 	found at the following page:
		https://github.com/Awallky/mystacktest.git
	
	***Get Crumb Info.***
	Open up your jenkins instance and login.
	Click on your username in the Jenkins toolbar at the top of your screen.
	Click on the configure tab on the left side of the page.
	You should see a section of the page labeled 'API Token.'
	Click the button under this labeled 'Show API Token...' and it will give you the necessary token to make requests to 
	your Jenkins instance via HTTP.
	To get the crumb information, type the following command and save it into a text file for later reference:
		CRUMB=$(curl -s 'http://USER:TOKEN@localhost:8080/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)')
		echo "$CRUMB" >> crumb.txt
		**Note: If you are having trouble getting the crumb using these commands, make sure that your Jenkins instance 
		        is running on port 8080.**
	Next, type the following command via a terminal and place the output into a text file:
		curl -X POST http://API_USER_ID:API_TOKEN@JENKINS_URL/job/JOB_NAME/build -H "$CRUMB"
	You should be able to stop builds using the following command:
		curl -I -X POST http://login_name:API TOKEN@localhost:8080/job/JOB NAME/BUILD NUMBER/stop -H 
		"Jenkins-Crumb:JENKINS CRUMB NUMBER"
	This shows that you are ready to install the plugin and make the necessary changes to the source code.
	* Here are a couple of useful links if there is any confusion about the instructons:
		1. Get Jenkins Crumb
			https://stackoverflow.com/questions/28577551/how-to-disable-a-jenkins-job-via-curl
		2. Get API User Token
			http://www.inanzzz.com/index.php/post/jnrg/running-jenkins-build-via-command-line
	
	Now change the source code in the testInPrgress_Extended/src/plugin/index.jelly file around line 37 
	so that it matches your project's build name, build number, and special crumb and access tokens that you just got
	from the previous steps.
	
	* The following is a very helpful link in what is required to make an HTTP request to Jenkins 
	  to abort a build for a specific job:
		http://www.inanzzz.com/index.php/post/jnrg/running-jenkins-build-via-command-line
	
	**Install testInProgress_Extended**
	Open a command line terminal and enter the plugin's root directory.
	Type mvn clean package.
	Check the target/ directory and find the testInProgress.hpi file location. This will be your new plugin to add to jenkins.
	Open up your instance of jenkins and perform the following steps:
		Go to configure jenkins (should look like a grey cog on the side toolbar on the left side of the page).
		Go to Manage Plugins (should lok like a green puzzle piece).
		There will be 4 different tabs available presented to the user. Select the advanced tab (should be the last one).
		Go to the section titled "Upload Plugin" and upload your previously located .hpi file. 
		Upload it to your jenkins instance and restart jenkins.
		You should now see the testInProgress_Extended plugin applied.
		
	* A helpful link for installing the .hpi plugin:
		https://jenkins.io/doc/book/managing/plugins/#advanced-installation
***TODO***
	Allow jenkins to find the user name and use it for the abort build request
	Allow jenkins to find the job name and use it for the abort build request
	Allow jenkins to find the build number and use it for the abort build request
	Make a more polished button; Much friendlier user experience than it currently has
	Only prompt the user when there is a test failure, otherwise hide this button
