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
                                 return 15000;
                              case 2:
                                  return 15500;
                              case 3:
                                  return 15000;
                              case 4:
                                  return 14000;
                              case 5:
                                  return 12500;
                              default:
                                   return 0;
                              }
                        }
                        
                        
                        public static int damage1(int value){
                             switch(value){
                              case 1:
                                 return 125;
                              case 2:
                                  return 145;
                              case 3:
                                  return 155;
                              case 4:
                                  return 165;
                              case 5:
                                  return 180;
                              default:
                                   return 0;
                              }
                        }
                        
                        
                        public static int damage2(int value){
                             switch(value){
                              case 1:
                                 return 145;
                              case 2:
                                  return 155;
                              case 3:
                                  return 165;
                              case 4:
                                  return 180;
                              case 5:
                                  return 200;
                              default:
                                   return 0;
                              }
                        }
                        
                        public static int damage3(int value){
                             switch(value){
                              case 1:
                                 return 180;
                              case 2:
                                  return 200;
                              case 3:
                                  return 225;
                              case 4:
                                  return 240;
                              case 5:
                                  return 260;
                              default:
                                   return 0;
                              }
                        }
	}
}