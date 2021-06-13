package com.example.bunonbasket.data.local.db

import com.example.bunonbasket.data.models.LoginModel
import com.example.bunonbasket.utils.helpers.EntityMapper
import javax.inject.Inject

/**
 * Created by inan on 13/6/21
 */
class CacheMapper
@Inject
constructor() : EntityMapper<UserEntity, LoginModel> {
    override fun mapFromEntity(entity: UserEntity): LoginModel {
        return LoginModel(
            address = entity.address,
            avatar = entity.avatar,
            avatar_original = entity.avatar_original,
            balance = entity.balance,
            city = entity.city,
            country = entity.country,
            created_at = entity.created_at,
            email = entity.email,
            email_verified_at = entity.email_verified_at,
            id = entity.id,
            name = entity.name,
            phone = entity.phone,
            postal_code = entity.postal_code,
            provider_id = entity.provider_id,
            token = entity.token,
            updated_at = entity.updated_at,
            user_type = entity.user_type
        )
    }

    override fun mapToEntity(domainModel: LoginModel): UserEntity {
        return UserEntity(
            address = domainModel.address,
            avatar = domainModel.avatar,
            avatar_original = domainModel.avatar_original,
            balance = domainModel.balance,
            city = domainModel.city,
            country = domainModel.country,
            created_at = domainModel.created_at,
            email = domainModel.email,
            email_verified_at = domainModel.email_verified_at,
            id = domainModel.id,
            name = domainModel.name,
            phone = domainModel.phone,
            postal_code = domainModel.postal_code,
            provider_id = domainModel.provider_id,
            token = domainModel.token,
            updated_at = domainModel.updated_at,
            user_type = domainModel.user_type
        )
    }

    fun mapFromEntityList(entities: List<UserEntity>): List<LoginModel> {
        return entities.map {
            mapFromEntity(it)
        }
    }
}