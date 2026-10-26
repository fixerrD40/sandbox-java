package practice;

public class StrategyPractice {

  public static void main(String[] args) {
    Animal person = new Animal();

    person.eat(1);
    person.move(Animal.Terrain.GROUND, Animal.Direction.EAST, 5);

    Animal seagull = new Animal(
        new Animal.EatStrategy() {
          @Override
          public void doEat(int amount) {
            System.out.println(String.format("Steal %d french fries!", amount));
          }
        },
        new CommonMovement.FlyStrategy()
    );

    seagull.eat(5);
    seagull.move(Animal.Terrain.CHASM, Animal.Direction.NORTH, 3);
  }

  private static class Animal {
    private final EatStrategy eatStrategy;
    private final MoveStrategy moveStrategy;

    public Animal() {
      this.eatStrategy = new EatStrategy() {};
      this.moveStrategy = new MoveStrategy() {};
    }

    public Animal(EatStrategy eatStrategy, MoveStrategy moveStrategy) {
      this.eatStrategy = eatStrategy;
      this.moveStrategy = moveStrategy;
    }

    public void eat(int amount) {
      this.eatStrategy.doEat(amount);
    }

    public boolean move(Terrain terrain, Direction direction, int distance) {
      return this.moveStrategy.doMove(terrain, direction, distance);
    }

    public static enum Terrain {
      GROUND, WATER, CHASM;

      @Override
      public String toString() {
        return this.name().toLowerCase();
      }
    }

    public static enum Direction {
      NORTH, SOUTH, EAST, WEST;

      @Override
      public String toString() {
        String lower = this.name().toLowerCase();
        return this.name().substring(0, 1) + lower.substring(1);
      }
    }

    private interface EatStrategy {
      default public void doEat(int amount) {
        System.out.println(String.format("Eat %d food.", amount));
      }
    }

    private interface MoveStrategy {
      public static final String FAILED = "Cannot proceed.";

      default public boolean doMove(Terrain terrain, Direction direction, int distance) {
        if (terrain == Terrain.GROUND) {
          System.out.println(String.format("Move %s %d on %s.", direction, distance, terrain));
          return true;
        }

        System.out.println(FAILED);
        return false;
      }
    }
  }

  public static class CommonMovement {

    public static class FlyStrategy implements StrategyPractice.Animal.MoveStrategy {
      @Override
      public boolean doMove(StrategyPractice.Animal.Terrain terrain, StrategyPractice.Animal.Direction direction, int distance) {
        System.out.println(String.format("Fly %s %d over %s.", direction, distance, terrain));
        return true;
      }
    }

    public static class SwimStrategy implements StrategyPractice.Animal.MoveStrategy {
      @Override
      public boolean doMove(StrategyPractice.Animal.Terrain terrain, StrategyPractice.Animal.Direction direction, int distance) {
        if (terrain == StrategyPractice.Animal.Terrain.WATER) {
          System.out.println(String.format("Swim %s %d through %s.", direction, distance, terrain));
          return true;
        }

        System.out.println(StrategyPractice.Animal.MoveStrategy.FAILED);
        return false;
      }
    }
  }
}
