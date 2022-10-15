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

def get_subfolders(PARENT_FOLDER){
    // list that contains all the folders to process the files
    List<String> subfolders = [];
    subfolders.add(PARENT_FOLDER);

    dh = new File(PARENT_FOLDER);

    // iterates recursively and gets all the subfolders
    dh.eachFileRecurse {
        // if there is not a dot in the file path, we add that to the subfolder list.
        if(it.name.indexOf('.') < 0){
            subfolders.add(it.absolutePath);
        }
    }
    return subfolders;
}

def get_textfiles(FOLDER){
    // list that contains all the text files in FOLDER
    List<String> textfiles = [];

    dh = new File(FOLDER);

    // iterates and gets all the text files
    dh.eachFile {
        // if the file ends with .txt, its added to the list
        if(it.name.endsWith('.txt')){
            textfiles.add(it.name);
        }
    }
    return textfiles;
}

def process_file(FOLDER, FILE, ORIGINAL_TEXT, REPLACE_TEXT){
    def replace = { File source, String toSearch, String replacement ->
        source.write(source.text.replaceAll(toSearch, replacement))
    }
    file = new File(FOLDER + "\\" + FILE);

    file.write(file.text.replaceAll(ORIGINAL_TEXT, REPLACE_TEXT));
    IJFDKJFKJD;
}


def process_folder(FOLDER, ORIGINAL_TEXT, REPLACE_TEXT){
    println("\n PROCESSING: " + FOLDER);

    textfiles = get_textfiles(FOLDER);
    
    // for each file in current folder, process it
    textfiles.each{
        process_file(FOLDER, it, ORIGINAL_TEXT, REPLACE_TEXT);
    };
    println("\n \n");

};


def process_all(PARENT_FOLDER, ORIGINAL_TEXT, REPLACE_TEXT, MODIFIED_FILES_LIST){
    // get all subfolders in parent folder
    List<String> sub_folders = get_subfolders(PARENT_FOLDER);

    println("* Here is the list of all the folders that are going to be processed: \n");
    sub_folders.each{ 
        println("- " + it) 
    };

    // for each sub folder, process the files in it
    sub_folders.each{ 
        process_folder(it, ORIGINAL_TEXT, REPLACE_TEXT);
    };

}