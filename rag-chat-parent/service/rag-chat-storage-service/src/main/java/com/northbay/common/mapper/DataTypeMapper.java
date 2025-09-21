package com.northbay.common.mapper;

import java.util.Collection;

public interface DataTypeMapper<E, D> {

	E toEntity(D dto);

	D toModel(E entity);

	Collection<E> toEntityList(Collection<D> modelList);

	Collection<D> toModelList(Collection<E> entityList);
}
