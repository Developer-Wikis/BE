package com.developer.wiki.oauth.util;

import com.developer.wiki.question.command.domain.MainCategory;
import com.developer.wiki.question.command.domain.NotMatchCategoryException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum ExtType {
    JPG("jpg"),PNG("png"),JPEG("jpeg");
    private final String value;

    public static String validType(String type) {
        if (type.isEmpty()) {
            throw new NotMatchCategoryException("파일 오류 입니다.");
        }
        for (ExtType ext : ExtType.values()) {
            if (ext.getValue().equals(type.toLowerCase())) {
                return ext.getValue();
            }
        }
        throw new NotMatchCategoryException("확장자 타입 오류입니다.");
    }
}