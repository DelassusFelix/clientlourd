package application;
import java.io.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.util.Date;

public class Fichier {
    private   BufferedWriter fW;
    private   BufferedReader fR;
    private char mode;
    public void ouvrir(String nomDuFichier, String s) throws IOException{

        mode = (s.toUpperCase()).charAt(0);
        File f = new File(nomDuFichier);
        if (mode == 'R' || mode == 'L')
            fR  = new BufferedReader(new FileReader(f));
        else     if (mode == 'W' || mode == 'E')
            fW = new BufferedWriter(new FileWriter(f));

    }
    public void fermer() throws IOException {
        if (mode == 'R' || mode == 'L') fR.close();
        else     if (mode == 'W' || mode == 'E')     fW.close();
    }
    public String lire() throws IOException {
        String chaine = fR.readLine();
        return chaine;
    }
    public void ecrire(int tmp) throws IOException {
        String chaine = "";
        chaine = String.valueOf(tmp);

        if (chaine != null)   {
            fW.write(chaine,0,chaine.length());
            fW.newLine();
        }
    }

    public void ecrire(String chaine) throws IOException {

        if (chaine != null)   {
            fW.write(chaine,0,chaine.length());
            fW.newLine();
        }
    }



    public void generePdf(Client leClient) {
        try {
            // Nom du fichier PDF à générer
            String pdfFileName = "stockPdfRelance/relanceContrat" + leClient.getRaisonSociale() + ".pdf";
            pdfFileName = pdfFileName.replace(" ", "");

            // Crée un PdfWriter pour écrire dans le fichier
            PdfWriter writer = new PdfWriter(pdfFileName);

            // Crée un PdfDocument pour gérer le document PDF
            PdfDocument pdf = new PdfDocument(writer);

            // Crée un Document pour ajouter du contenu
            Document document = new Document(pdf);

            // Ajoute du texte au document
            document.add(new Paragraph("A l'attention de " + leClient.getRaisonSociale()));
            document.add(new Paragraph("Bonjour cher client, \n Votre contrat de maintenance arrive à expiration le " + leClient.getLeContrat().getDateEcheance() + ". \n Pour continuer à bénéficier de nos services, pensez à nous recontacter avant cette date afin de prolonger votre contrat."));
            document.add(new Paragraph("Merci pour votre confiance ! \n Le " + new Date() + ", L'équipe CashCash."));
            // Ferme le document et le fichier PDF
            document.close();

            System.out.println("Le fichier PDF a été généré avec succès : " + pdfFileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

