import java.nio.file.Files;
import java.nio.file.Paths;
import static groovy.io.FileType.FILES;

static void main(String[] args) { 

    // Se calcula la cantidad de argumentos una sola vez
    args_size = args.size();
    if((args_size == 3) || (args_size == 4)){
        String PARENT_FOLDER = args[0];
        String ORIGINAL_TEXT = args[1];
        String REPLACE_TEXT = args[2];
        String MODIFIED_FILES_LIST = "";
        
        if(args_size == 4){
            MODIFIED_FILES_LIST = args[3];
        }

        /* TODO
            1. EXTRAER FOLDERS DESDE EL FILES_DIRECTORY
            2. CREAR FUNCION QUE VERIFICA SI HAY PALABRA EN ARCHIVO
            3. CREAR 

        */

        if (Files.isDirectory(Paths.get(PARENT_FOLDER))) {
            println("\nThe input folder exists, the processing will begin... \n");
            process_all(PARENT_FOLDER, ORIGINAL_TEXT, REPLACE_TEXT, MODIFIED_FILES_LIST);
        }

        else{
            println("\nThe input folder does not exist or is incorrect, please try again");
        }

    }

    else{
        println("\nInvalid arguments number");
    }
}

def get_hello(){
    return "hello world";
}

def get_subfolders(parent_folder){
    // list that contains all the folders to process the files
    List<String> subfolders = [];
    subfolders.add(parent_folder);

    dh = new File(parent_folder);

    // iterates recursively and gets all the subfolders
    dh.eachFileRecurse {
        // if there is not a dot in the file path, we add that to the subfolder list.
        if(it.name.indexOf('.') < 0){
            subfolders.add(it.absolutePath);
        }
    }
    return subfolders;
}

def get_textfiles(folder){
    // list that contains all the text files in FOLDER
    List<String> textfiles = [];

    dh = new File(folder);

    // iterates and gets all the text files
    dh.eachFile {
        // if the file ends with .txt, its added to the list
        if(it.name.endsWith('.txt')){
            textfiles.add(it.name);
        }
    }
    return textfiles;
}

def get_ocurrences(file_obj, sub_sequence){
    return file.text.count(sub_sequence);
}
    

def process_file(folder, textfile, original_text, replace_text, processed_files){
    // Opens the file to be processed
    absolute_path = folder + "\\" + textfile
    file = new File(absolute_path);

    // Check if the replace text is on the text
    occurences = get_ocurrences(file, original_text);
    println(occurences);

    // If there are occurrences, replace the text
    if(occurences > 0){
        println("---> FILE " + textfile + " HAS " + occurences.toString() + " OCURRENCES, READY TO BE PROCESSED");
        processed_files.add(absolute_path);
    }

    else{
        println("---> FILE " + textfile + " HAS 0 OCURRENCES, ITS GONNA BE IGNORED")
    }
    //println(PROCESSED_FILES);

    //file.write(file.text.replaceAll(original_text, replace_text));
    //IJFDKJFKJD;
}


def process_folder(folder, original_text, replace_text, processed_files){
    println("\n+ PROCESSING FOLDER: " + folder);

    textfiles = get_textfiles(folder);
    
    // for each file in current folder, process it
    textfiles.each{
        process_file(folder, it, original_text, replace_text, processed_files);
    };
    println("\n \n");

};


def process_all(parent_folder, original_text, replace_text, MODIFIED_FILES_LIST){
    // List of processed files
    List<String> processed_files = [];

    // get all subfolders in parent folder
    List<String> sub_folders = get_subfolders(parent_folder);

    println("* Here is the list of all the folders that are going to be processed: \n");
    sub_folders.each{ 
        println("- " + it) 
    };

    // for each sub folder, process the files in it
    sub_folders.each{ 
        process_folder(it, original_text, replace_text, processed_files);
    };

    println(processed_files);

}