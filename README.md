# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* This project use for file selection
* version 1.0

### How do I get set up? ###

* get clone repo
* Add Permissions In Manifest
<uses-permission android:name="android.permission.INTERNET" />
   <uses-permission android:name="android.permission.MEDIA_CONTENT_CONTROL" />
   <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
   <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
	
* Add this lines to your Activity

         ArrayList<String> fileType = new ArrayList<String>();
	    
	    fileType.add(".jpg");
            
	    fileType.add(".doc");
             
	     //use for multiple selection
	     Constants.setMultipleSelection(true);
      
      FileSelectorActivity.newInstant(context, 111, fileType);
	   
	    
	    
  * Add this method to your Activity  
  
   @Overrid 
   protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    
    {
    
        if (requestCode == 111 && resultCode == RESULT_OK) {
            if (data != null) {
                ArrayList<String> list = data.getStringArrayListExtra(FILE_KEY);
            }
        }	}


### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact
