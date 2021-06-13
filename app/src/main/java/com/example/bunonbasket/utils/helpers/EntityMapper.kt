package com.example.bunonbasket.utils.helpers

/**
 * Created by inan on 13/6/21
 */
interface EntityMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel
    fun mapToEntity(domainModel: DomainModel): Entity
}