# Files processing with Groovy

This is an exercise for the Accenture interview process.

## Prequisites:
* Java
* Groovy

Follow this tutorial to install to install both: [Link to youtube here](https://www.youtube.com/watch?v=16kvUkp8Z3M&ab_channel=AmitThinks)

## How to run the script

Do a cd where the program.groovy file is located and then:
```console
groovy program.groovy [PATH_TO_DIRECTORY_TO_PROCESS] [WORD_TO_REPLACE] [REPLACEMENT_WORD] [OUTPUT_PROCESSED_FILES](optional)
```
Example:

```console
cd "path/to/program"
```

```console
groovy program.groovy "C:\path_to_folder" ipsum new_ipsum "C:\changed_files.txt"
```

## This is how the output of an execution looks

```text
The input folder exists, the processing will begin...

##############################

######## INITIAL STAGE #######

##############################

* Here is the list of all the folders that are going to be processed:

- C:\Users\Runder\Documents\test\input
- C:\Users\Runder\Documents\test\input\fake_text.txt
- C:\Users\Runder\Documents\test\input\sub_folder_1
- C:\Users\Runder\Documents\test\input\sub_folder_1\sub_folder_2

##############################

###### PROCESSING STAGE ######

##############################

+ PROCESSING FOLDER: C:\Users\Runder\Documents\test\input
---> FILE file_1.txt HAS 1 OCURRENCES, READY TO BE PROCESSED

+ PROCESSING FOLDER: C:\Users\Runder\Documents\test\input\fake_text.txt

+ PROCESSING FOLDER: C:\Users\Runder\Documents\test\input\sub_folder_1
---> FILE file_2_not_have.txt HAS 0 OCURRENCES, ITS GONNA BE IGNORED

+ PROCESSING FOLDER: C:\Users\Runder\Documents\test\input\sub_folder_1\sub_folder_2
---> FILE file_3.txt HAS 1 OCURRENCES, READY TO BE PROCESSED
---> FILE file_4.txt HAS 1 OCURRENCES, READY TO BE PROCESSED

*** CREATING OUTPUT FILE OF MODIFIED FILES
--> FILE CREATED SUCCESSFULLY ON PATH: C:\Users\Runder\Documents\test\changes.txt

##############################

######## FINAL STAGE #########

##############################

TOTAL PROCESSED FILES: 3
PROGRAM TIME DURATION: 0.078 seconds
```

