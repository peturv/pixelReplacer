package se.omnivore.gfx.pixelReplacer;

import com.google.common.base.Preconditions;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public class ArgumentParser {
    File image;
    Color replacementColor;
    Color exclusionColor;

    public ArgumentParser(String[] args){
        validateArguments(args);
        parseArguments(args);
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        if (!image.exists()) {
            throw new RuntimeException(new FileNotFoundException("File " + image + " does not exist"));
        }
        this.image = image;
    }

    public void setImage(String image) {
        this.setImage(new File(image));
    }

    public int getReplacementColor() {
        return replacementColor.getRGB();
    }

    public void setReplacementColor(Color replacementColor) {
        this.replacementColor = replacementColor;
    }

    public void setReplacementColor(String replacementColor) {
        this.setReplacementColor(Color.decode(replacementColor));
    }

    public int getExclusionColor() {
        return exclusionColor.getRGB();
    }

    public void setExclusionColor(Color exclusionColor) {
        this.exclusionColor = exclusionColor;
    }

    public void setExclusionColor(String exclusionColor) {
        this.setExclusionColor(Color.decode(exclusionColor));
    }

    void validateArguments(String[] args) {
        try {
            Preconditions.checkNotNull(args);
            Preconditions.checkArgument(args.length == 3);
        } catch (Exception e) {
            throw new RuntimeException("Invalid arguments");
        }
    }

    void parseArguments(String[] args) {
        this.setImage(args[0]);
        this.setReplacementColor(args[1]);
        this.setExclusionColor(args[2]);
    }
}