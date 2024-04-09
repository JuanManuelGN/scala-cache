package cache

import com.github.benmanes.caffeine.cache.Caffeine
import scalacache.caffeine._
import scalacache._
import cats.effect.IO

import java.util.concurrent.TimeUnit

object CafCache extends App {

  case class User(id: Long, name: String)

  val underlyingCaffeineCache =
    Caffeine
      .newBuilder()
      .maximumSize(20)
      .expireAfterWrite(1, TimeUnit.SECONDS)
      .build[String, Entry[User]]()

  implicit val customisedCaffeineCache: Cache[User] =
    CaffeineCache(underlyingCaffeineCache)

  implicit val mode: Mode[IO] = scalacache.CatsEffect.modes.async

  val user1 = User(1, "juan")
  val user2 = User(2, "alberto")

  customisedCaffeineCache.put("1")(user1).unsafeRunSync()
  customisedCaffeineCache.put("2")(user2).unsafeRunSync()

  val userGet1 = customisedCaffeineCache.get("1")
  val userGet2 = customisedCaffeineCache.get("2")
  userGet1.unsafeRunSync().fold(println("nothing in the cache"))(println)
  userGet2.unsafeRunSync().fold(println("nothing in the cache"))(println)

  val userGet11 = customisedCaffeineCache.get("1")
  userGet11.unsafeRunSync().fold(println("nothing in the cache"))(println)

  Thread.sleep(2000)

  val userGet111 = customisedCaffeineCache.get("1")
  userGet111.unsafeRunSync().fold(println("nothing in the cache"))(println)

}
