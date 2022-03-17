package server;

import com.google.gson.annotations.SerializedName;

public class ServerRequest {
    String description;
    Files files;

    /* Representation Invariants */
    // files can never be null

    @SerializedName("public")
    Boolean public_gist;

    public ServerRequest() {}
    public ServerRequest(String description, String content, Boolean scope) {
        this.description = description;
        this.files = new Files(content);
        this.public_gist = scope;
    }

    public void setFiles(Files files) {this.files = files;}
    public void setDescription(String description) {this.description = description;}

    class Files {

        @SerializedName("hello_world.txt")
        File file;

        public Files(String _content) {
            file = new File(_content);
        }


        class File {
            String content;

            public File(String _content) {content = _content;}
        }

    }

}
