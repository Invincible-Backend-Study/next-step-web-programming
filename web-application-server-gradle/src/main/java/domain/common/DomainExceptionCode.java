package domain.common;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum DomainExceptionCode {
    USER(100);


    private final int code;
}
