import java.nio.file.Files;
import java.nio.file.Paths;
import static groovy.io.FileType.FILES;

import groovy.time.TimeCategory 
import groovy.time.TimeDuration

static void main(String[] args) { 

    /*
        Arguments:
        args[0] String: Path to folder that is going to be processed.
        args[1] String: Word that is going to be replaced.
        args[2] String: Word that is going to replace args[1].
        (optional) args[3] String: Path to output file that contains the list of modified files.
    */
    args_size = args.size();
    if((args_size == 3) || (args_size == 4)){
        String ROOT_FOLDER = args[0];
        String ORIGINAL_TEXT = args[1];
        String REPLACE_TEXT = args[2];
        String MODIFIED_FILES_FILE = "";
        
        if(args_size == 4){
            MODIFIED_FILES_FILE = args[3];
        }

        if (Files.isDirectory(Paths.get(ROOT_FOLDER))) {
            println("\nThe input folder exists, the processing will begin...");
            process_all(ROOT_FOLDER, ORIGINAL_TEXT, REPLACE_TEXT, MODIFIED_FILES_FILE);
        }

        else{
            println("\nThe input folder does not exist or is incorrect, please try again");
        }

    }

    else{
        println("\nInvalid arguments number");
    }
}

def create_folder(path_of_folder){
    /*
        Description:
            Creates a folder giving a path
        Parameters:
            String path_of_folder: path where the folder is going to be created
        Returns:
            void
    */
    folder = new File(path_of_folder);
    folder.mkdir();
}

def copy_file(source, destiny){
    /*
        Description:
            Copies the file on parameter source on the path of destiny parameter.
        Parameters:
            String source: Source file.
            String destiny: destiny file.
        Returns:
            void
    */
    def src = new File(source);
    def dst = new File(destiny);
    dst.text = "";
    dst << src.text;
}

def write_list_to_textfile(path_of_file, list_of_strings){
    /*
        Description:
            Writes a list of strings on a specified path.
        Parameters:
            String path_of_file: Output file where the list is going to be written.
            String list_of_strings: List of strings that is going to be written on the file.
        Returns:
            void
    */
    File file = new File(path_of_file);
    file.text = "";
    list_of_strings.each { val -> file << val+"\n"};
}


def get_subfolders(folder){
    /*
        Description:
            Giving a folder path, gets all the subfolders inside of it.
        Parameters:
            String folder: Folder path on the OS.
        Returns:
            List<String>
    */
    // list that contains all the folders to process the files
    List<String> subfolders = [];
    subfolders.add(folder);

    dir = new File(folder);

    // iterates recursively and gets all the subfolders
    dir.eachFileRecurse {
        // if there is not a dot in the file path, we add that to the subfolder list.
        //if(it.name.indexOf('.') < 0){
        if(it.isDirectory()){
            subfolders.add(it.absolutePath);
        }
    }
    return subfolders;
}

def get_textfiles(folder){
    /*
        Description:
            Giving a folder, gets all the text files (files that ends on .txt) inside of it.
        Parameters:
            String folder: Folder path on the OS.
        Returns:
            List<String>
    */
    // list that contains all the text files in FOLDER
    List<String> textfiles = [];

    dh = new File(folder);

    // iterates and gets all the text files
    dh.eachFile {
        // if the file ends with .txt, its added to the list
        if(it.name.endsWith('.txt') && it.isFile()){
            textfiles.add(it.name);
        }
    }
    return textfiles;
}

def get_ocurrences(file_obj, sub_sequence){
    /*
        Description:
            Giving a .txt file object, counts all the ocurrences of a text subsequence.
        Parameters:
            File file_obj: File object of a .xt
            String sub_sequence: text subsequence.
        Returns:
            int
    */
    return file.text.count(sub_sequence);
}
    

def process_file(folder, textfile, original_text, replace_text, processed_files, root_folder, backup_folder){
    /*
        Description:
            Main function of the file processing, call other functios declared before.
        Parameters:
            String folder: Path of a folder to process files.
            String textfile: File inside of folder.
            String original_text: text to replace.
            String replace_text: new text to replace the original.
            List<String> processed_files: List that keeps track of processed files.
            String root_folder: path of root folder, needed because this method is called on all subfolders.
            String backup_folder: backup folder for files.
        Returns:
            void
    */
    // Opens the file to be processed
    absolute_path = folder + "\\" + textfile
    file = new File(absolute_path);

    // Check if the replace text is on the text
    occurences = get_ocurrences(file, original_text);

    // If there are occurrences, replace the text
    if(occurences > 0){
        // Log the occurrences
        println("---> FILE " + textfile + " HAS " + occurences.toString() + " OCURRENCES, READY TO BE PROCESSED");

        // Keep track of processed files
        processed_files.add(absolute_path);

        // Move original file to the backup folder
        copy_file(absolute_path, absolute_path.replace(root_folder, backup_folder));

        // Change the original text with the replacement text
        file.write(file.text.replaceAll(original_text, replace_text));  
    }

    else{
        println("---> FILE " + textfile + " HAS 0 OCURRENCES, ITS GONNA BE IGNORED")
        // Move original file to the backup folder
        copy_file(absolute_path, absolute_path.replace(root_folder, backup_folder));
    }
}

def process_folder(current_folder, original_text, replace_text, processed_files, root_folder, backup_folder){
    /*
        Description:
            Main function of the folder processing, iterates over files on an specific path.
        Parameters:
            String current_folder: Path of a folder to process files.
            String original_text: text to replace.
            String replace_text: new text to replace the original.
            List<String> processed_files: List that keeps track of processed files.
            String root_folder: path of root folder, needed because this method is called on all subfolders.
            String backup_folder: backup folder for files.
        Returns:
            void
    */

    // First, create backup folder
    create_folder(current_folder.replace(root_folder, backup_folder));

    // get all textfiles in folder
    textfiles = get_textfiles(current_folder);
    
    // for each file in current folder, process it
    textfiles.each{
        process_file(current_folder, it, original_text, replace_text, processed_files, root_folder, backup_folder);
    };

}

def create_modified_files_file(file_path, processed_files){
    /*
        Description:
            Giving a list of strings, creates a new .txt file containing all the elements on it.
        Parameters:
            String file_path: Path of file.
            List<String> processed_files: List of elements that are going to be written on the file.
        Returns:
            void
    */

    if(file_path.endsWith('.txt')){
        try {
            write_list_to_textfile(file_path, processed_files);
            println("--> FILE CREATED SUCCESSFULLY ON PATH: "+file_path)
        } 
        
        catch(Exception ex) {
            println("--> THERE WAS AN ERROR TRYING TO CREATE THE LIST OF PROCESSED FILES, CHECK PATH. THE FILE IS NOT BEING CREATED THIS TIME")
        }
    }

    else {
        println("--> MODIFIED FILES FILE PATH HAS TO END ON .txt, THE FILE IS NOT BEING CREATED THIS TIME");
    }
}

def process_all(root_folder, original_text, replace_text, modified_files_file){
    /*
        Description:
            Main function that calls of the sub main methods declared above.
            Logs and principal methods can be found here.
        Parameters:
            String root_folder: Root folder that is going to be processed.
            String original_text: text to replace on files.
            String replace_text: new text to replace the original on files.
            String modified_files_file: Path for output file.
        Returns:
            void
    */

    Date start = new Date();
    
    // Variables needed for the backup and track of changed files
    List<String> processed_files = [];
    backup_folder = root_folder + "_backup";

    // Creates backupfolder before start
    create_folder(backup_folder);

    // get all subfolders in parent folder
    List<String> sub_folders = get_subfolders(root_folder);

    println("\n##############################");
    println("\n######## INITIAL STAGE #######");
    println("\n##############################\n");

    println("* Here is the list of all the folders that are going to be processed: \n");
    sub_folders.each{ 
        println("- " + it);
    };

    println("\n##############################");
    println("\n###### PROCESSING STAGE ######");
    println("\n##############################");

    // for each sub folder, process the files in it
    sub_folders.each{ 
        println("\n+ PROCESSING FOLDER: " + it);
        process_folder(it, original_text, replace_text, processed_files, root_folder, backup_folder);

    };

    // create the modified files file
    if((modified_files_file.length() > 0) && (processed_files.size())){
        println("\n*** CREATING OUTPUT FILE OF MODIFIED FILES")
        create_modified_files_file(modified_files_file, processed_files);
    }

    println("\n##############################");
    println("\n######## FINAL STAGE #########");
    println("\n##############################\n");

    println("TOTAL PROCESSED FILES: " + processed_files.size())

    Date stop = new Date();
    TimeDuration td = TimeCategory.minus(stop, start);
    println("PROGRAM TIME DURATION: " + td.toString());
}