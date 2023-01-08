package ru.library.My_library_project.client;

import ru.library.My_library_project.api.data.Book;

import javax.swing.*;
import java.awt.*;

public class BookRenderer extends DefaultListCellRenderer {


    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
     super.getListCellRendererComponent(list, value ,index, isSelected, cellHasFocus);
     setText(((Book)value).getName()+" - "+((Book)value).getAuthor());
     return this;
    }
}
