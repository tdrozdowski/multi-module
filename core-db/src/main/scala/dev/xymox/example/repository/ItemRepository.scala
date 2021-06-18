package dev.xymox.example.repository

import io.getquill.context.ZioJdbc.QDataSource
import zio._
import zio.macros.accessible

@accessible
trait ItemRepository extends Queries {
  def create(item: ItemRecord): Task[ItemRecord]
  def all: Task[Seq[ItemRecord]]
  def findById(id: Long): Task[ItemRecord]
}

object ItemRepository {
  val layer: URLayer[QDataSource, Has[ItemRepository]] = (ItemRepositoryLive(_, _)).toLayer
}
