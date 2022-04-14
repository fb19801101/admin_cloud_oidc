package com.provider_oidc_gongxu.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;


/**
 * JodaTime 反序列化将字符串转化为datetime
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-04-28 19:23
 */

public class DatetimeDeserializer extends JsonDeserializer<DateTime> {
	private static DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

	@Override
	public DateTime deserialize(JsonParser jp, DeserializationContext context) throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp); String s = node.asText();

		DateTime parse = DateTime.parse(s, dateFormatter); return parse;
	}
}
