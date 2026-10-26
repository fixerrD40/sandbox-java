package practice;

public class AnimalPractice {

  public static void main(String[] args) {
    Animal person = new Animal.Builder().build();

    person.eat(1);
    person.move(Animal.Terrain.GROUND, Animal.Direction.EAST, 5);

    Animal seagull = new Animal.Builder()
      .withEatStrategy(
        new Animal.EatStrategy() {
          @Override
          public void doEat(int amount) {
            System.out.println(String.format("Steal %d french fries!", amount));
          }
        }
      )
      .withMoveStrategy(MoveStrategies.FLY)
      .build();

    seagull.eat(5);
    seagull.move(Animal.Terrain.CHASM, Animal.Direction.NORTH, 3);
  }

  private static class Animal {
    private final EatStrategy eatStrategy;
    private final MoveStrategy moveStrategy;

    private Animal(EatStrategy eatStrategy, MoveStrategy moveStrategy) {
      this.eatStrategy = eatStrategy;
      this.moveStrategy = moveStrategy;
    }

    // Use Builder pattern to customize animal behavior
    public static class Builder {
      private MoveStrategy moveStrategy = (terrain, direction, distance) -> {
        if (terrain == Terrain.GROUND) {
          System.out.println(String.format("Move %s %d on %s.", direction, distance, terrain));
          return true;
        }

        System.out.println(String.format("Cannot move on %s", terrain));
        return false;
      };

      private EatStrategy eatStrategy = amount -> {
        System.out.println(String.format("Eat %d food.", amount));
      };

      public Builder withMoveStrategy(MoveStrategy movementStrategy) {
        this.moveStrategy = movementStrategy;
        return this;
      }
      
      public Builder withEatStrategy(EatStrategy eatStrategy) {
        this.eatStrategy = eatStrategy;
        return this;
      }

      public Animal build() {
        return new Animal(this.eatStrategy, this.moveStrategy);
      }
    }

    public void eat(int amount) {
      this.eatStrategy.doEat(amount);
    }

    public boolean move(Terrain terrain, Direction direction, int distance) {
      return this.moveStrategy.execute(terrain, direction, distance);
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

    // Use Strategy pattern to open up behavior functionality
    private interface EatStrategy {
      public void doEat(int amount);
    }

    private interface MoveStrategy {
      public boolean execute(Terrain terrain, Direction direction, int distance);
    }
  }

  public enum MoveStrategies implements Animal.MoveStrategy {

    FLY {
      @Override
      public boolean execute(Animal.Terrain terrain, Animal.Direction direction, int distance) {
        System.out.println(String.format("Fly %s %d over %s.", direction, distance, terrain));
        return true;
      }
    },

    SWIM {
      @Override
      public boolean execute(Animal.Terrain terrain, Animal.Direction direction, int distance) {
        if (terrain == Animal.Terrain.WATER) {
          System.out.println(String.format("Swim %s %d through %s.", direction, distance, terrain));
          return true;
        }

        System.out.println(String.format("Cannot swim through %s", terrain));
        return false;
      }
    }
  }
}
