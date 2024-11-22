/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package image;

/**
 *
 * @author alumne
 */
public class Image {
    int id;
    String title;
    String description;
    String date;
    String author;
    String keywords;
    String filename;
    String creator;

    public Image(int id, String title, String author, String keywords, String filename, String date, String creator, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.author = author;
        this.keywords = keywords;
        this.filename = filename;
        this.creator = creator;
    }
}

