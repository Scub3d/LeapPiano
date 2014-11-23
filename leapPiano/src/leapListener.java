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
    }

    @Override
	public void onDisconnect(Controller controller) {
        System.out.println("Disconnected");
    }

    @Override
	public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    public float[][] currentFingerTips = new float[10][3];
    public float[][] previousFingerTips = new float[10][3];
    public pianoComponent pComp = new pianoComponent();
    
	@Override
	public void onFrame(Controller controller) {
        Frame current_frame = controller.frame();
        Frame previous_frame = controller.frame(1);
        
        FingerList currentFingers = current_frame.fingers();
        FingerList previousFingers = previous_frame.fingers();
            
        this.pComp.updateData(currentFingers, previousFingers);
               
    }
}
