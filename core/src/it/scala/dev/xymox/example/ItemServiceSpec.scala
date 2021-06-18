package dev.xymox.example

import dev.xymox.example.repository.{ItemRecord, ItemRepository}
import io.getquill.context.ZioJdbc.QDataSource
import zio._
import zio.blocking._
import zio.console._
import zio.test.environment.TestEnvironment
import zio.test._
import zio.magic._

object ItemServiceSpec extends DefaultRunnableSpec {

  val testLayer: TaskLayer[Has[ItemService]] =
    ZLayer.wire[Has[ItemService]](Console.live, Blocking.live >>> QDataSource.fromPrefix("someprefix"), ItemRepository.layer, ItemService.layer)

  override def spec: ZSpec[TestEnvironment, Any] = suite("ItemServiceSpec")(
    testM("create") {
      val createItemRequest = CreateItemRequest(name = "Test", description = "a description", price = 19.99)
      val itemRecord        = ItemRecord(name = "Test Too", description = "another description", unitPrice = 250.00)
      for {
        results  <- ItemService.create(createItemRequest)
        item     <- ItemService.get(results.id)
        notright <- ItemRepository.create(itemRecord)
      } yield assertTrue(results.id != -1) && assertTrue(results.id == item.id)
    }
  ).inject(testLayer.orDie)
}
