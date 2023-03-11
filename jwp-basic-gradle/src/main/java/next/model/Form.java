package next.model;

public class Form {

    private final String contents;
    private final String title;
    private final String id;

    public Form(String contents, String title, String id) throws Exception {
        this.contents = contents;
        this.title = title;
        this.id = id;
        if (contents == null || contents.equals("")) {
            throw new Exception("내용이 존재하지 않습니다.");
        }
        if (title == null || title.equals("")) {
            throw new Exception("제목의 내용이 존재하지 않습니다.");
        }
        if (id == null || id.equals("")) {
            throw new Exception("질문이 존재하지 않습니다..");
        }
    }

    public String getContents() {
        return contents;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
