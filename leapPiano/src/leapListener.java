/******************************************************************************\
* Copyright (C) 2012-2013 Leap Motion, Inc. All rights reserved.               *
* Leap Motion proprietary and confidential. Not for distribution.              *
* Use subject to the terms of the Leap Motion SDK Agreement available at       *
* https://developer.leapmotion.com/sdk_agreement, or another agreement         *
* between Leap Motion and you, your company or other organization.             *
\******************************************************************************/

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;

class leapListener extends Listener {

	@Override
	public void onInit(Controller controller) {
        System.out.println("Initialized");
    }
    
    @Override
	public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
    }

    @Override
	public void onDisconnect(Controller controller) {
        System.out.println("Disconnected");
    }

    @Override
	public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    public Hand hand;
    public int[][] keys;
    public float[][] currentFingerTips = new float[10][3];
    public float[][] previousFingerTips = new float[10][3];
    
	@Override
	public void onFrame(Controller controller) {
        Frame current_frame = controller.frame();    // 3.0f , 1f, .2f also works okay
        Frame previous_frame = controller.frame(1);
        
        FingerList currentFingers = current_frame.fingers();
        FingerList previousFingers = previous_frame.fingers();
        
        for(Finger finger : currentFingers) {
        	System.out.println(finger.tipPosition());
        }
        
        
                
        
        GestureList gestures = current_frame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);

            switch (gesture.type()) {
                case TYPE_SWIPE:
		            break;
		            
		        case TYPE_SCREEN_TAP:
		            break;
		                         
                default:
                    break;
            }
        }
    }
}
