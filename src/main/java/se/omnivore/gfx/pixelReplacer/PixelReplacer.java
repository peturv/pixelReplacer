package se.omnivore.gfx.pixelReplacer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PixelReplacer {


    public PixelReplacer() {
    }

    File replaceAndWrite(ArgumentParser arguments, String newFilePrefix) throws IOException {
        if(newFilePrefix == null) {
            newFilePrefix = "pixelReplaced";
        }

        BufferedImage img = readImage(arguments.getImage());
        replacePixels(arguments, img);
        return writeFile(arguments, img, newFilePrefix);
    }

    void replacePixels(ArgumentParser arguments, BufferedImage img) {
        int imgHeight = img.getHeight();
        int imgWidth = img.getWidth();

        for(int y = 0; y < imgHeight; y++) {

            for(int x = 0; x < imgWidth; x++) {

                if(img.getRGB(x, y) != arguments.getExclusionColor()) {
                    img.setRGB(x, y, arguments.getReplacementColor());
                }

            }
        }
    }

    BufferedImage readImage(File image) throws IOException {
        try {
            return ImageIO.read(image);
        } catch (IOException e) {
            throw new IOException("Could not read file: " + image.getPath(), e);
        }
    }

    File writeFile(ArgumentParser arguments, BufferedImage img, String newFilePrefix) throws IOException {
        String oldImageFileName = arguments.getImage().getAbsolutePath();
        String type = oldImageFileName.substring(oldImageFileName.lastIndexOf(".")+1).toLowerCase();
        File newImage = new File(arguments.getImage().getParentFile().getAbsolutePath() + File.separator + newFilePrefix + "_" + arguments.getImage().getName());
        try {
            ImageIO.write(img, type, newImage);
            return newImage;
        } catch (IOException e) {
            throw new IOException("Could not write file.", e);
        }
    }

    private static void printUsage() {
        System.out.println("Usage: pixelReplacer.jar image replacementColor doNotReplaceColor");
        System.out.println("for colors use rgb codes including preceding hash; #cecece.");
    }

    public static void main(String[] args) {

        ArgumentParser arguments;

        try {
            arguments = new ArgumentParser(args);
        } catch (RuntimeException e) {
            e.printStackTrace();
            PixelReplacer.printUsage();
            System.exit(-1);
            return;
        }

        try {
            PixelReplacer pr = new PixelReplacer();
            File newFile = pr.replaceAndWrite(arguments, null);
            System.out.println("Finished. New file written to :" + newFile.getPath());
            System.exit(0);
        } catch (RuntimeException e) {
            e.printStackTrace();
            PixelReplacer.printUsage();
            System.exit(-1);
        } catch (IOException ioe) {
            System.err.print(ioe.getMessage());
            ioe.printStackTrace();
            System.exit(-1);
        }
    }
}
