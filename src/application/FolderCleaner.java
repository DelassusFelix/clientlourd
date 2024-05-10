package application;

import java.io.File;
import java.io.IOException;

public class FolderCleaner {

    /**
     * Efface tout le contenu d'un dossier, y compris les sous-dossiers.
     *
     * @param directory Le dossier dont le contenu doit être supprimé.
     * @throws IOException Si une erreur se produit lors de la suppression des fichiers.
     */
    public static void clearFolder(File directory) throws IOException {
        // Vérifiez si le fichier fourni est un dossier
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Ce n'est pas un dossier: " + directory);
        }

        // Récupère tous les fichiers et dossiers dans le dossier
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Si c'est un sous-dossier, supprimez son contenu de manière récursive
                    clearFolder(file);
                    // Ensuite, supprimez le sous-dossier vide
                    file.delete();
                } else {
                    // Si c'est un fichier, supprimez-le
                    file.delete();
                }
            }
        }
    }

}
