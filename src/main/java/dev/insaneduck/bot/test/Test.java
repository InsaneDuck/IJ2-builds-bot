package dev.insaneduck.bot.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import dev.insaneduck.bot.modal.Build;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Test
{
    public static void main(String[] args) throws IOException
    {
        String json = FileUtils.readFileToString(new File("/home/satya/Downloads/IJ2builds.json"), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Build> builds = new ArrayList<>();
        JsonNode jsonNode = objectMapper.readTree(json);
        ArrayNode arrayNode = (ArrayNode) jsonNode.get("builds");
        for (JsonNode node : arrayNode)
        {
            Build build = objectMapper.treeToValue( node,Build.class);
            System.out.println(build);
            builds.add(build);
        }
    }
}
