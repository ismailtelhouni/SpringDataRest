package org.spring.data.rest.exception.handler;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorMessage {

	String message;
	Date timestamp;
	Integer code;
	
}
