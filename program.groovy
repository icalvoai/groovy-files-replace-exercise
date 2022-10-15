import java.nio.file.Files;
import java.nio.file.Paths;
import static groovy.io.FileType.FILES;

static void main(String[] args) { 
    
    // Se calcula la cantidad de argumentos una sola vez
    args_size = args.size()
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
            println("The input folder exists, the processing will begin...");

            sub_folders = get_subfolders(PARENT_FOLDER);
            println(sub_folders);
        }

        else{
            println("The input folder does not exist or is incorrect, please try again");
        }

    }

    else{
        println("Invalid arguments number");
    }
}

String get_hello(){
    return "hello world";
}

String[] get_subfolders(PARENT_FOLDER){

    dh = new File(PARENT_FOLDER);
    dh.eachFileRecurse {
        
        println(it)
    }
    return 0;
}