package server;

import com.google.gson.annotations.SerializedName;

public class ServerResponse {
    String url;
    String forks_url;
    String commits_url;
    String id;
    String node_id;
    String git_pull_url;
    String git_push_url;
    String html_url;
    String created_at;
    String updated_at;
    String description;
    int comments;

    Files files;

    public class Files {

        @SerializedName("hello_world.txt")
        File file;

        public Files(String _content) {file = new File(_content);}
        public File getFile() {return file;}


        public class File {
            String content;

            public File(String _content) {content = _content;}
            public String getContent() {return content;}
        }

    }


    public ServerResponse() {}

    public Files getFiles() {
        return files;
    }

    public String getID() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.files.getFile().getContent();
    }
}
