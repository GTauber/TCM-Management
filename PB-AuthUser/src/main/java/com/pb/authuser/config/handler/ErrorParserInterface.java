package com.pb.authuser.config.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pb.authuser.models.entity.Response;
import org.reactivestreams.Publisher;

@FunctionalInterface
public interface ErrorParserInterface {

    Publisher<Void> parse(Response<?> error) throws JsonProcessingException;

}
