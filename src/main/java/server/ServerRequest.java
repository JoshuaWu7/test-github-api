package server;

import com.google.gson.annotations.SerializedName;

public class ServerRequest {
    String description;

    Files files;

    @SerializedName("public")
    Boolean public_gist;

    public ServerRequest(String description, String content, Boolean scope) {
        this.description = description;
        this.files = new Files(content);
        this.public_gist = scope;
    }

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
