package com.enigna.logging.loggers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Component
public class MyApplicationRunner implements CommandLineRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(MyApplicationRunner.class);
    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Application started");
        try{
//            int x = 10, y = 0;
//            int z = x / y;
//            System.out.println("result: "+ z);
            copyLogFile();

        } catch (IOException e){
            LOGGER.error("Une Erreur est survenue: Le chemin d’accès spécifié est introuvable ");
            }

        LOGGER.info("Application finished");
        }
        public void copyLogFile() throws IOException {
            File logFile = new File("logs/log_file.log");
            File logFileBackup = new File("logs/old/log_file.log");
// verification ou creation du répertoire 'logs/'
            File logs = logFile.getParentFile();
            if (!logs.exists()) {
                boolean dirsCreated = logs.mkdirs();
                if (!dirsCreated) {
                    throw new IOException("Impossible de créer le répertoire " + logs.getAbsolutePath());
                }
            }
            // verification ou creation du fichier 'log_file'
            if (!logFile.exists()) {
                boolean fileCreated = logFile.createNewFile();
                if (!fileCreated) {
                    throw new IOException("Impossible de créer le fichier " + logFile.getAbsolutePath());
                }
            }
            // verification ou creation du répertoire 'logs/old/'
            File oldLogsDir = logFileBackup.getParentFile();
            if (!oldLogsDir.exists()) {
                boolean oldDirsCreated = oldLogsDir.mkdirs();
                if (!oldDirsCreated) {
                    throw new IOException("Impossible de créer le répertoire " + oldLogsDir.getAbsolutePath());
                }
            }
            //copie des fichiers
            Files.copy(logFile.toPath(), logFileBackup.toPath(), StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("Le fichier journal "+ logFile.getPath() +" a été copié avec succès vers " + logFileBackup.getPath());
        }
}
