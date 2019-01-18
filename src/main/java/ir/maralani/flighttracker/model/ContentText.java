package ir.maralani.flighttracker.model;


import javax.persistence.*;

@Entity
@Table(name = "content_text")
public class ContentText extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private TextContext textContext;

    @Column(length = 1500)
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TextContext getTextContext() {
        return textContext;
    }

    public void setTextContext(TextContext textContext) {
        this.textContext = textContext;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public enum TextContext {
        BANNER
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentText)) return false;

        ContentText that = (ContentText) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null && getTextContext() == that.getTextContext() && (getText() != null ? getText().equals(that.getText()) : that.getText() == null);

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTextContext() != null ? getTextContext().hashCode() : 0);
        result = 31 * result + (getText() != null ? getText().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContentText{" +
                "id=" + id +
                ", textContext=" + textContext +
                ", text='" + text + '\'' +
                '}';
    }
}
