package com.example.application;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import java.io.ByteArrayInputStream;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class MainView extends VerticalLayout {
  private final ImageService imageService;

  public MainView(@Autowired ImageService imageService) {
    this.imageService = imageService;

    setSizeFull();
    setJustifyContentMode(JustifyContentMode.CENTER);
    setAlignItems(Alignment.CENTER);

    TextField inputField = new TextField("Gib einen Prompt ein:");
    inputField.setWidth("300px");

    Button generateButton = new Button("Bild generieren");
    generateButton.getStyle().set("background-color", "#007bff").set("color", "white");

    Image image = new Image();
    image.setMaxWidth("500px");
    image.getStyle().set("border-radius", "10px").set("margin-top", "20px");

    Button saveButton = new Button("Bild speichern");
    saveButton.setEnabled(false);
    saveButton.getStyle().set("background-color", "#28a745").set("color", "white");

    generateButton.addClickListener(event -> {
      byte [] bytes = imageService.generateImage(inputField.getValue(), "anime", "1");
      StreamResource streamResource = new StreamResource("image.jpg",() -> new ByteArrayInputStream(bytes));

      image.setSrc(streamResource);
      saveButton.setEnabled(true);
      saveButton.addClickListener(e -> imageService.saveImage(bytes,inputField.getValue()));
    });

    add(inputField, generateButton, image, saveButton);
  }
}
