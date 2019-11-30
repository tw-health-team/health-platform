package com.theus.health.core.model;

import lombok.Data;

/**
 * @author tangwei
 * @date 2019-11-30 16:42
 */
@Data
public class CharacterElement {
    private int unicode;

    private String wubi;

    public CharacterElement() {}

    public CharacterElement(String str) {
        if (str != null) {
            String[] content = str.split(",");
            int length = 3;
            if (content.length == length) {
                try {
                    this.unicode = Integer.parseInt(content[0]);
                } catch (Exception e) {
                    System.out.println("CharacterElement: " + e.getMessage());
                }
                // this.pinyin = content[1];
                this.wubi = content[2].split("[|]")[0];
            }
        }
    }
}
