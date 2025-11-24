package com.mydb.meta;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mydb.exceptions.MetadataException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * File-system backed {@link MetadataManager} that persists {@link Metadata} as JSON on disk.
 * <p>
 * The implementation intentionally avoids external frameworks beyond Gson to keep the
 * persistence format human-readable while still maintaining strict parsing rules.
 */
public class JsonMetadataManager implements MetadataManager {

    private final Path metadataPath;
    private final Gson gson = new Gson();

    /**
     * Creates a new manager bound to a metadata path.
     *
     * @param metadataPath path to {@code metadata.json}
     */
    public JsonMetadataManager(Path metadataPath) {
        this.metadataPath = metadataPath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Metadata load() throws IOException, MetadataException {
        try {
            if (!Files.exists(metadataPath)) {
                return null;
            }
            String json = Files.readString(metadataPath, StandardCharsets.UTF_8);
            return gson.fromJson(json, Metadata.class);
        } catch (JsonSyntaxException e) {
            throw new MetadataException("Unable to parse metadata.json", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Metadata metadata) throws IOException, MetadataException {
        try {
            String json = gson.toJson(metadata);
            Files.writeString(metadataPath, json, StandardCharsets.UTF_8);
        } catch (JsonSyntaxException e) {
            throw new MetadataException("Unable to serialize metadata", e);
        }
    }
}
