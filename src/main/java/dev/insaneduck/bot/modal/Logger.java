package dev.insaneduck.bot.modal;

import lombok.Data;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Data
public class Logger
{
    File logFile;

    public void log(String logMessage) throws IOException
    {
        FileUtils.writeStringToFile(logFile,logMessage, StandardCharsets.UTF_8, logFile.exists());
    }
}
