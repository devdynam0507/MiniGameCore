package com.gamecore.data;

import com.gamecore.MiniGame;
import com.google.common.base.Charsets;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class DataManager extends YamlConfiguration{
    private File file;

    public DataManager(String fileName){
        this.file = new File(MiniGame.dir, fileName);
        try {
            if(!file.exists())
                file.createNewFile();
            this.load();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public DataManager(File dir, String fileName){
        if(!dir.exists())
            dir.mkdir();
        this.file = new File(dir, fileName);
        try {
            if(!file.exists())
                file.createNewFile();
            this.load();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void load(){
        try {
            super.load(this.file);
        }catch (InvalidConfigurationException | IOException e){
            e.printStackTrace();
        }
    }

    public void save() throws IOException, InvalidConfigurationException{
        Validate.notNull(this.file, "File cannot be null");
        String data = this.saveToString();
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);
        try{
            writer.write(data);
        }finally {
            writer.close();
        }

        super.save(this.file);
    }

    @Override
    public String saveToString() {
        String data = new String();
        boolean first = true;
        for (String s : super.saveToString().split("\\\\u")) {
            if (s.length() >= 4 && !first) {
                data += (char) Integer.parseInt(s.substring(0, 4), 16);
                if (s.length() >= 5) {
                    data += s.substring(4);
                }
            } else {
                data += s;
                first = false;
            }
        }
        return data;
    }


}
