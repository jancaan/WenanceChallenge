package com.wenance.challenge.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Transformer<S, T> implements Function<S, T> {

   private Function<S, T> objectConstructor;

   private final List<BiConsumer<S, T>> objectFields = new ArrayList<>();

   private class FieldMapper {

      private T mapFields(final S source, final T target) {
         objectFields.forEach(f -> f.accept(source, target));
         return target;
      }

   }

   @Override
   public T apply(final S source) {
      return apply(source, objectConstructor.apply(source));
   }

   public T apply(final S source, final T target) {
      return new FieldMapper().mapFields(source, target);
   }

   public <O> Transformer<S, T> fields(final Function<S, O> getter, final BiConsumer<T, O> setter) {
      objectFields.add((source, target) -> setter.accept(target, getter.apply(source)));
      return this;
   }

   public <O1, O2> Transformer<S, T> fields(final Function<S, O1> getter, final BiConsumer<T, O2> setter, final Function<O1, O2> converter) {
      objectFields.add((source, target) -> {
         final O1 value = getter.apply(source);
         final O2 converted = value == null ? null : converter.apply(value);
         setter.accept(target, converted);
      });
      return this;
   }

   public Transformer<S, T> constructor(final Supplier<T> constructor) {
      this.objectConstructor = (dontuse) -> constructor.get();
      return this;
   }

   public <O> Transformer<S, T> constructor(final Function<O, T> construct, final Function<S, O> param1) {
      this.objectConstructor = (S source) -> construct.apply(param1.apply(source));
      return this;
   }

   public <O1, O2> Transformer<S, T> constructor(final BiFunction<O1, O2, T> construct, final Function<S, O1> param1, final Function<S, O2> param2) {
      this.objectConstructor = (S source) -> construct.apply(param1.apply(source), param2.apply(source));
      return this;
   }

   public static <S, T> Transformer<S, T> mapping(final Class<S> source, final Class<T> target) {
      return new Transformer<>();
   }

}