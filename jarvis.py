# J.A.R.V.I.S. AI Assistant Implementation
# Complete implementation with bug fixes and improvements

import os
import datetime
import json
import time
import threading
import speech_recognition as sr
import pyttsx3
import webbrowser
import platform
import re
import logging
from pathlib import Path

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    filename='jarvis.log'
)
logger = logging.getLogger('JARVIS')

class JARVIS:
    def __init__(self):
        self.name = "JARVIS"
        self.is_active = False
        self.recognizer = sr.Recognizer()
        
        # Initialize speech engine
        try:
            self.speech_engine = pyttsx3.init()
            self.speech_engine.setProperty('rate', 180)
            self.speech_engine.setProperty('volume', 1.0)
            
            # Set voice based on available voices
            voices = self.speech_engine.getProperty('voices')
            for voice in voices:
                if "male" in voice.name.lower():
                    self.speech_engine.setProperty('voice', voice.id)
                    break
            logger.info("Speech engine initialized successfully")
        except Exception as e:
            logger.error(f"Speech engine initialization failed: {e}")
            print(f"Speech engine initialization failed: {e}")
        
        # Command history and context memory
        self.command_history = []
        self.context = {}
        
        # Load configuration
        self.config = self._load_config()
        self.wake_word = self.config["wake_word"]
        
        # System information
        self.system_info = {
            "os": platform.system(),
            "version": platform.version(),
            "processor": platform.processor()
        }
        
        logger.info(f"JARVIS initialized on {self.system_info['os']} system")
        
    def _load_config(self):
        """Load configuration from file if exists, otherwise create default"""
        config_path = Path.home() / ".jarvis" / "config.json"
        if config_path.exists():
            try:
                with open(config_path, 'r') as f:
                    return json.load(f)
            except Exception as e:
                logger.error(f"Failed to load config: {e}")
                return self._create_default_config(config_path)
        else:
            return self._create_default_config(config_path)
    
    def _create_default_config(self, config_path):
        """Create default configuration file"""
        default_config = {
            "user_name": "Sir",
            "wake_word": "jarvis",
            "voice_rate": 180,
            "smart_devices": {},
            "preferred_apps": {
                "browser": "default",
                "code_editor": "default",
                "music": "default"
            },
            "api_keys": {}
        }
        
        try:
            # Ensure directory exists
            os.makedirs(os.path.dirname(config_path), exist_ok=True)
            
            # Save default config
            with open(config_path, 'w') as f:
                json.dump(default_config, f, indent=4)
            
            logger.info("Created default configuration file")
        except Exception as e:
            logger.error(f"Failed to create config file: {e}")
            print(f"Failed to create config file: {e}")
            
        return default_config
    
    def speak(self, text):
        """Convert text to speech"""
        logger.info(f"JARVIS says: {text}")
        print(f"{self.name}: {text}")
        try:
            self.speech_engine.say(text)
            self.speech_engine.runAndWait()
        except Exception as e:
            logger.error(f"Speech engine failed: {e}")
            print(f"Speech failed (see console output): {text}")
    
    def listen(self):
        """Listen for user command"""
        try:
            with sr.Microphone() as source:
                print("Listening...")
                self.recognizer.adjust_for_ambient_noise(source, duration=1)
                audio = self.recognizer.listen(source, timeout=5, phrase_time_limit=5)
                
                try:
                    text = self.recognizer.recognize_google(audio).lower()
                    print(f"You said: {text}")
                    logger.info(f"User said: {text}")
                    return text
                except sr.UnknownValueError:
                    print("Sorry, I didn't catch that.")
                    return ""
                except sr.RequestError as e:
                    logger.error(f"Speech recognition service error: {e}")
                    self.speak("I'm having trouble accessing my speech recognition service.")
                    return ""
        except Exception as e:
            logger.error(f"Microphone initialization failed: {e}")
            self.speak("I'm having trouble accessing the microphone.")
            return self.get_text_input()
    
    def get_text_input(self):
        """Fallback to text input"""
        return input("Please type your command: ").lower()

    def get_user_input(self):
        """Get user input through speech or text"""
        try:
            return self.listen()
        except Exception as e:
            logger.warning(f"Speech recognition failed, falling back to text input: {e}")
            return self.get_text_input()

    def process_command(self, command):
        """Process user command"""
        if not command:
            return
            
        # Add to history
        self.command_history.append({
            "command": command,
            "timestamp": datetime.datetime.now().isoformat()
        })
        
        # Process wake word
        if self.wake_word in command.lower() and not self.is_active:
            self.is_active = True
            self.speak(f"Yes, {self.config['user_name']}?")
            command = self.get_user_input()  # Get the actual command after wake word
            if not command:
                self.is_active = False
                return
        
        # If not active and no wake word, ignore
        if not self.is_active and self.wake_word not in command.lower():
            return
        
        # Handle system commands
        if any(phrase in command for phrase in ["shutdown", "turn off", "exit", "quit"]):
            self.confirm_action("Are you sure you want to shutdown the system?", self.shutdown_system)
            self.is_active = False
            return
            
        # Handle time questions
        if "time" in command:
            self.tell_time()
            self.is_active = False
            return
            
        # Handle opening applications
        app_match = re.search(r"open\s+(\w+)", command)
        if app_match:
            app_name = app_match.group(1)
            self.open_application(app_name)
            self.is_active = False
            return
            
        # Handle web searches
        if "search for" in command or "look up" in command:
            search_term = command.split("search for")[-1].strip() if "search for" in command else command.split("look up")[-1].strip()
            self.web_search(search_term)
            self.is_active = False
            return
            
        # Handle smart home commands
        if any(device in command for device in ["lights", "thermostat", "camera"]):
            self.control_smart_device(command)
            self.is_active = False
            return
            
        # Handle general knowledge questions
        if any(q in command for q in ["what is", "who is", "when", "where", "why", "how"]):
            self.answer_question(command)
            self.is_active = False
            return
            
        # Handle visual processing requests
        if "analyze image" in command or "what's in this image" in command:
            self.speak("Please show me the image you'd like me to analyze.")
            # In a real implementation, this would trigger camera or file selection
            # self.analyze_image(image_path)
            self.is_active = False
            return
            
        # Default response for unrecognized commands
        self.speak("I'm not sure how to help with that. Would you like me to search the web for information?")
        response = self.get_user_input()
        if any(word in response.lower() for word in ["yes", "yeah", "sure", "okay"]):
            self.web_search(command)
        
        # Reset active status after processing
        self.is_active = False
    
    def tell_time(self):
        """Tell the current time"""
        current_time = datetime.datetime.now()
        time_str = current_time.strftime("%I:%M %p")
        self.speak(f"The time is {time_str}")
    
    def open_application(self, app_name):
        """Open specified application"""
        app_name = app_name.lower()
        
        # Dictionary of common applications and their commands
        apps = {
            "browser": self._open_browser,
            "chrome": lambda: self._open_app("chrome"),
            "firefox": lambda: self._open_app("firefox"),
            "code": lambda: self._open_app("code"),
            "spotify": lambda: self._open_app("spotify"),
            "word": lambda: self._open_app("word"),
            "excel": lambda: self._open_app("excel"),
            "notepad": lambda: self._open_app("notepad")
        }
        
        if app_name in apps:
            self.speak(f"Opening {app_name}")
            apps[app_name]()
        else:
            self.speak(f"I don't know how to open {app_name}. Would you like me to search for installation information?")
            response = self.get_user_input()
            if any(word in response.lower() for word in ["yes", "yeah", "sure", "okay"]):
                self.web_search(f"how to install {app_name}")
    
    def _open_browser(self):
        """Open default web browser"""
        try:
            webbrowser.open("https://www.google.com")
            logger.info("Opened default browser")
        except Exception as e:
            logger.error(f"Failed to open browser: {e}")
            self.speak("I'm having trouble opening the browser")
    
    def _open_app(self, app_name):
        """Open application based on operating system"""
        os_name = platform.system()
        
        try:
            if os_name == "Windows":
                os.system(f"start {app_name}")
            elif os_name == "Darwin":  # macOS
                os.system(f"open -a {app_name}")
            elif os_name == "Linux":
                os.system(f"{app_name} &")
            
            logger.info(f"Opened application: {app_name}")
        except Exception as e:
            logger.error(f"Failed to open {app_name}: {e}")
            self.speak(f"I'm sorry, I couldn't open {app_name}")
    
    def web_search(self, query):
        """Perform a web search"""
        self.speak(f"Searching for {query}")
        try:
            formatted_query = query.replace(" ", "+")
            search_url = f"https://www.google.com/search?q={formatted_query}"
            webbrowser.open(search_url)
            logger.info(f"Web search performed: {query}")
        except Exception as e:
            logger.error(f"Web search failed: {e}")
            self.speak("I'm having trouble performing the web search")
        
    def control_smart_device(self, command):
        """Control smart home devices"""
        # This is a placeholder - would connect to smart home APIs
        if "lights" in command:
            if "on" in command:
                self.speak("Turning on the lights")
                logger.info("Smart home action: Lights on")
            elif "off" in command:
                self.speak("Turning off the lights")
                logger.info("Smart home action: Lights off")
            elif "dim" in command:
                self.speak("Dimming the lights")
                logger.info("Smart home action: Dimming lights")
                
        elif "thermostat" in command:
            if "set" in command and "to" in command:
                try:
                    temp_match = re.search(r"set.*?to (\d+)", command)
                    if temp_match:
                        temp = temp_match.group(1)
                        self.speak(f"Setting thermostat to {temp} degrees")
                        logger.info(f"Smart home action: Thermostat set to {temp}")
                    else:
                        self.speak("I'm not sure what temperature you want to set")
                except Exception as e:
                    logger.error(f"Thermostat command parsing error: {e}")
                    self.speak("I'm not sure what temperature you want to set")
            else:
                self.speak("Would you like me to check or change the thermostat setting?")
                
        elif "camera" in command:
            if "show" in command:
                self.speak("Displaying camera feed")
                logger.info("Smart home action: Displaying camera feed")
                # Would call a function to display camera feed
    
    def answer_question(self, question):
        """Answer general knowledge questions"""
        # In a real implementation, this would connect to a knowledge base or LLM API
        self.speak("Let me search for information to answer your question.")
        self.web_search(question)
    
    def analyze_image(self, image_path):
        """Analyze image content using computer vision"""
        # This is a placeholder for computer vision functionality
        self.speak("I would analyze the image and describe what I see.")
        
        # In a real implementation, this would use a computer vision API or library
        # For now, just log the attempt
        logger.info(f"Image analysis requested for: {image_path}")
    
    def confirm_action(self, message, action_function):
        """Ask for confirmation before performing sensitive actions"""
        self.speak(message)
        response = self.get_user_input()
        
        if any(word in response.lower() for word in ["yes", "confirm", "sure", "proceed"]):
            action_function()
        else:
            self.speak("Action cancelled.")
    
    def shutdown_system(self):
        """Shutdown the computer"""
        self.speak("Shutting down the system. Goodbye.")
        logger.info("System shutdown initiated")
        
        os_name = platform.system()
        try:
            if os_name == "Windows":
                os.system("shutdown /s /t 10")
            elif os_name == "Darwin":  # macOS
                os.system("sudo shutdown -h +1")  # Shutdown in 1 minute
            elif os_name == "Linux":
                os.system("sudo shutdown -h +1")  # Shutdown in 1 minute
        except Exception as e:
            logger.error(f"Shutdown command failed: {e}")
            self.speak("I'm having trouble shutting down the system")
    
    def get_time_of_day(self):
        """Return appropriate greeting based on time of day"""
        hour = datetime.datetime.now().hour
        
        if 5 <= hour < 12:
            return "morning"
        elif 12 <= hour < 17:
            return "afternoon"
        else:
            return "evening"
            
    def run(self):
        """Main execution loop for JARVIS"""
        self.speak(f"JARVIS online. Good {self.get_time_of_day()}, {self.config['user_name']}.")
        
        try:
            while True:
                if not self.is_active:
                    # Only actively listen for wake word when not already active
                    command = self.get_user_input()
                    if command:
                        self.process_command(command)
                else:
                    # If active, process the next command
                    command = self.get_user_input()
                    if command:
                        self.process_command(command)
                        
                time.sleep(0.1)  # Short sleep to prevent high CPU usage
        except KeyboardInterrupt:
            self.speak("JARVIS shutting down.")
            logger.info("JARVIS terminated by keyboard interrupt")
        except Exception as e:
            logger.critical(f"JARVIS encountered a critical error: {e}")
            self.speak("I've encountered an error and need to restart.")

# Example of how to run JARVIS
if __name__ == "__main__":
    jarvis = JARVIS()
    jarvis.run()