package utilz;

import java.awt.event.KeyEvent;

public class Constants {

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int GROUND = 4;
		public static final int DEAD = 5;
		public static final int ATTACK_1 = 6;
		public static final int ATTACK_JUMP_1 = 7;
		public static final int ATTACK_JUMP_2 = 8;
                public static final int HURT = 9;
                

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case RUNNING:
				return 6;
			case IDLE:
				return 5;
                        case HURT:        
                                return 1;
			case DEAD:
				return 4;
			case JUMP:
                                return 3;
			case ATTACK_1:
                                return 3;
			case ATTACK_JUMP_1:
                                return 5;
			case ATTACK_JUMP_2:
				return 5;
			case GROUND:
				return 2;
			case FALLING:
			default:
				return 1;
			}
		}
                
                        public static int maxhealth(int value){
                             switch(value){
                              case 1:
                                 return 5000;  // Rhino - tanky
                              case 2:
                                  return 4500; // Beheaded - glass cannon
                              case 3:
                                  return 4500; // Ender - mid
                              case 4:
                                  return 4000; // Plague Doctor - low HP, has poison
                              case 5:
                                  return 5500; // Paladin - tanky, has heal
                              default:
                                   return 4500;
                              }
                        }
                        
                        
                        public static int damage1(int value){
                             switch(value){
                              case 1:
                                 return 350;  // Rhino basic
                              case 2:
                                  return 400; // Beheaded basic
                              case 3:
                                  return 380; // Ender basic
                              case 4:
                                  return 420; // Plague Doc basic
                              case 5:
                                  return 300; // Paladin basic (lower, has heal)
                              default:
                                   return 350;
                              }
                        }
                        
                        
                        public static int damage2(int value){
                             switch(value){
                              case 1:
                                 return 600;  // Rhino skill2
                              case 2:
                                  return 700; // Beheaded skill2
                              case 3:
                                  return 650; // Ender skill2
                              case 4:
                                  return 550; // Plague Doc skill2 (also poisons)
                              case 5:
                                  return 500; // Paladin skill2 (also shields)
                              default:
                                   return 600;
                              }
                        }
                        
                        public static int damage3(int value){
                             switch(value){
                              case 1:
                                 return 900;  // Rhino ultimate
                              case 2:
                                  return 1000; // Beheaded ultimate
                              case 3:
                                  return 950; // Ender ultimate
                              case 4:
                                  return 850; // Plague Doc ultimate
                              case 5:
                                  return 750; // Paladin ultimate (also heals)
                              default:
                                   return 850;
                              }
                        }
	}
}